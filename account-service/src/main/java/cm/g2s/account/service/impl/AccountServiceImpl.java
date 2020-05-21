package cm.g2s.account.service.impl;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.domain.model.AccountState;
import cm.g2s.account.infrastructure.repository.AccountRepository;
import cm.g2s.account.security.CustomPrincipal;
import cm.g2s.account.service.AccountService;
import cm.g2s.account.shared.dto.AccountDto;
import cm.g2s.account.shared.dto.AccountDtoPage;
import cm.g2s.account.shared.exception.ConflictException;
import cm.g2s.account.shared.exception.ResourceNotFoundException;
import cm.g2s.account.shared.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto create(CustomPrincipal principal,  AccountDto accountDto) {
        //We check if partner have account
        if(accountDto.getPartnerDto() != null && accountRepository.existsByPartnerId(accountDto.getPartnerDto().getId())) {
            log.error("{} already have an account!", accountDto.getPartnerDto().getLastName());
            throw new ConflictException(String.format("%s already have an account!", accountDto.getPartnerDto().getLastName()));
        }
        //We set creation date
        accountDto.setCreationDate(LocalDateTime.now());
        //We check if account number and key are empty, when we fill it
        if(accountDto.getNumber() == null || accountDto.getNumber().isEmpty())
            accountDto.setNumber(generateNumber());
        if(accountDto.getKey() == null || accountDto.getKey().isEmpty())
            accountDto.setKey(generateKey());
        //We check if account and key is used and we generate another
        Boolean exits = true;
        while (exits) {
            if(accountRepository.existsByNumberAndKey(accountDto.getNumber(),accountDto.getKey())) {
                accountDto.setNumber(generateNumber());
                accountDto.setKey(generateKey());
            } else {
                exits = false;
            }
        }
        if(accountDto.getBalance() == null)
            accountDto.setBalance(new BigDecimal("0.00"));
        val account = accountMapper.map(accountDto);
        //We set default state of account
        account.setState(AccountState.CREATED);
        return accountMapper.map(accountRepository.save(account));
    }

    @Override
    public void update(CustomPrincipal principal, AccountDto accountDto) {
        accountRepository.save(accountMapper.map(accountDto));
    }

    @Override
    public AccountDto findById(CustomPrincipal principal, String id) {
        val account = accountRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Account with id {} not found",id);
                    throw new ResourceNotFoundException(String.format("Account with id %s not found",id));
                }
        );
        return accountMapper.map(account);
    }

    @Override
    public AccountDto findByNumber(CustomPrincipal principal, String number) {
        Account account = accountRepository.findByNumber(number).orElseThrow(
                () -> {
                    log.error("Account with number {} not found",number);
                    throw new ResourceNotFoundException(String.format("Account with number %s not found",number));
                }
        );
        return accountMapper.map(account);
    }

    @Override
    public AccountDto findByPartnerId(CustomPrincipal principal, String partnerId) {
        val account = accountRepository.findByPartnerId(partnerId).orElseThrow(
                () -> {
                    log.error("Account with partner id {} not found",partnerId);
                    throw new ResourceNotFoundException(String.format("Account with partner id %s not found",partnerId));
                }
        );
        return accountMapper.map(account);
    }

    @Override
    public AccountDtoPage findAll(CustomPrincipal principal, String number, String partnerId, PageRequest pageRequest) {

        Page<Account> accountPage;

        if (!StringUtils.isEmpty(number)) {
            //search by category name
            accountPage = accountRepository.findByNumber(number, pageRequest);
        } else if (!StringUtils.isEmpty(partnerId)) {
            //search by partnerId
            accountPage = accountRepository.findByPartnerId(partnerId, pageRequest);
        }
        else{
            // search all
            accountPage = accountRepository.findAll(pageRequest);
        }

        return new AccountDtoPage(
                accountPage.getContent().stream().map(accountMapper::map).collect(Collectors.toList()),
                PageRequest.of(accountPage.getPageable().getPageNumber(),
                        accountPage.getPageable().getPageSize()),
                accountPage.getTotalElements()
        );
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        val account = accountRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Account with id {} not found",id);
                    throw new ResourceNotFoundException(String.format("Account with id %s not found",id));
                }
        );
        accountRepository.delete(account);
    }

    @Override
    public void debitAccount(String accountId, BigDecimal debitAmount) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> {
                    log.error("Account with id {} not found",accountId);
                    throw new ResourceNotFoundException(String.format("Account with id %s not found",accountId));
                }
        );
        account.setBalance(account.getBalance().add(debitAmount));
        account.setState(AccountState.PENDING);
        accountRepository.save(account);
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
