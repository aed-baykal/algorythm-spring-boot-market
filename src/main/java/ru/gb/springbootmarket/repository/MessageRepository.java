package ru.gb.springbootmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springbootmarket.model.MessageTextElement;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageTextElement, Long> {

    List<MessageTextElement> getByTitleContaining(String title);
}
