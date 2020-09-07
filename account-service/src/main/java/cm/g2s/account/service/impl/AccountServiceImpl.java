package cm.g2s.account.service.impl;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.domain.model.AccountState;
import cm.g2s.account.exception.ConflictException;
import cm.g2s.account.exception.ResourceNotFoundException;
import cm.g2s.account.infrastructure.repository.AccountRepository;
import cm.g2s.account.security.CustomPrincipal;
import cm.g2s.account.service.AccountService;
import cm.g2s.account.service.partner.model.PartnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account create(CustomPrincipal principal, Account account, PartnerDto partnerDto) {
        //We check if partner have account
        if(account.getPartnerId() != null && accountRepository.existsByPartnerId(account.getPartnerId())) {
            log.error("{} already have an account!", partnerDto.getLastName());
            throw new ConflictException(String.format("%s already have an account!", partnerDto.getLastName()));
        }
        //We set creation date
        account.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        //We check if account number and key are empty, when we fill it
        if(account.getNumber() == null || account.getNumber().isEmpty())
            account.setNumber(generateNumber());
        if(account.getKey() == null || account.getKey().isEmpty())
            account.setKey(generateKey());
        //We check if account and key is used and we generate another
        Boolean exits = true;
        while (exits) {
            if(accountRepository.existsByNumberAndKey(account.getNumber(),account.getKey())) {
                account.setNumber(generateNumber());
                account.setKey(generateKey());
            } else {
                exits = false;
            }
        }
        if(account.getBalance() == null)
            account.setBalance(BigDecimal.ZERO);
        //We set default state of account
        account.setState(AccountState.CREATED);
        return accountRepository.save(account);
    }

    @Override
    public void update(CustomPrincipal principal, Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findById(CustomPrincipal principal, String id) {
        return accountRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Account with id {} not found",id);
                    throw new ResourceNotFoundException(String.format("Account with id %s not found",id));
                }
        );
    }

    @Override
    public Account findByNumber(CustomPrincipal principal, String number) {
        return accountRepository.findByNumber(number).orElseThrow(
                () -> {
                    log.error("Account with number {} not found",number);
                    throw new ResourceNotFoundException(String.format("Account with number %s not found",number));
                }
        );
    }

    @Override
    public Account findByPartnerId(CustomPrincipal principal, String partnerId) {
        return accountRepository.findByPartnerId(partnerId).orElseThrow(
                () -> {
                    log.error("Account with partner id {} not found",partnerId);
                    throw new ResourceNotFoundException(String.format("Account with partner id %s not found",partnerId));
                }
        );
    }

    @Override
    public Page<Account> findAll(CustomPrincipal principal, String number, String key,
                                 String partnerId, PageRequest pageRequest) {

        Page<Account> accountPage;

        if (!StringUtils.isEmpty(number)) {
            //search by category name
            accountPage = accountRepository.findByNumber(number, pageRequest);
        } else if (!StringUtils.isEmpty(number) && !StringUtils.isEmpty(key)) {
            //search by number and key
            accountPage = accountRepository.findByNumberAndKey(number, key, pageRequest);
        } else if (!StringUtils.isEmpty(key)) {
            //search by key
            accountPage = accountRepository.findByKey(key, pageRequest);
        } else if (!StringUtils.isEmpty(partnerId)) {
            //search by partnerId
            accountPage = accountRepository.findByPartnerId(partnerId, pageRequest);
        }
        else{
            // search all
            accountPage = accountRepository.findAll(pageRequest);
        }

        return accountPage;
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {

        accountRepository.delete(findById(principal, id));
    }

    @Override
    public void debitAccount(CustomPrincipal principal, String accountId, BigDecimal debitAmount) {
        Account account = findById(principal, accountId);
        account.setBalance(account.getBalance().add(debitAmount));
        account.setState(AccountState.PENDING);
        accountRepository.save(account);
    }

    @Override
    public void confirmDeBitAccount(CustomPrincipal principal, String accountId, AccountState state) {
        Account account = findById(principal, accountId);
        account.setState(state);
        accountRepository.save(account);
    }

    @Override
    public Account findByUserId(CustomPrincipal principal, String userId) {
        Account account =  accountRepository.findByUserId(userId).orElseThrow(
                () -> {
                    log.error("Account with user id {} not found",userId);
                    throw new ResourceNotFoundException(String.format("Account with user id %s not found",userId));
                }
        );
        return account;
    }

    private String generateNumber(){
        val random = ThreadLocalRandom.current();
        return ((Long)  random.nextLong(1_000_000_000L, 10_000_000_000L)).toString();
    }

    private String generateKey() {
        val random = ThreadLocalRandom.current();
        return ((Long)  random.nextLong(10L, 100L)).toString();
    }
}
