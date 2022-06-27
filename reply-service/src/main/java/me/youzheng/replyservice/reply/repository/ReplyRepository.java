package me.youzheng.replyservice.reply.repository;

import me.youzheng.replyservice.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer>, ReplyRepositorySupporter {

}