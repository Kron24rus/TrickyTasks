package ru.mikheev.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mikheev.domain.Author;
import ru.mikheev.repostory.AuthorRepository;
import ru.mikheev.services.TransactionalService;

@Slf4j
@Lazy
@Service
public class TransactionalServiceImpl implements TransactionalService {

    private final AuthorRepository authorRepository;
    private final TransactionalService transactionalService;

    public TransactionalServiceImpl(AuthorRepository authorRepository, @Lazy TransactionalService transactionalService) {
        this.authorRepository = authorRepository;
        this.transactionalService = transactionalService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void transactionOne() {
        Author author = new Author();
        author.setName("test1");
        Author saved = authorRepository.save(author);
        log.info("author with id {} saved", saved.getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void transactionTwo() {
        log.info("method 2 called");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void transactionBoth() {
        //при таком вызове новые транзакции не будут созданы т.к мы вызывает не из бина (прокси) а из непосредственно
        //объекта поэтому новая транзакция не будет создана, чтобы это исправить нужно внедрить себя через Lazy и
        //вызвать методы от бина, тогда будет создаваться новая транзакция
        transactionOne();
        transactionalService.transactionTwo();
    }
}
