package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Dto.ForumSearchById;
import com.example.umc3_teamproject.domain.Forum;
import com.example.umc3_teamproject.domain.QForum;
import com.example.umc3_teamproject.exception.NoForumExist;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ForumRepository {


    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    // forum 저장
    public void save(Forum forum){
        if(forum.getId() == null){
            em.persist(forum);
        }else{
            em.merge(forum);
        }
    }

    // forum 하나 찾기
    public Forum findOne(Long forum_id){
        Forum forum = em.find(Forum.class, forum_id);
        if(forum == null){
            throw new NoForumExist("forum_id가 " + forum_id + "인 forum은 존재하지 않습니다.");
        }
        if(forum.isDeleted_status() == false){
            return forum;
        }else{
            return null;
        }
    }

    // forum과 user fetch join
    // forum은 to one 관계가 user밖에 없어서 쓸 일이 없을 것 같다.
    public List<Forum> findAllWithUser(){
        return em.createQuery("select f from Forum f " +
                " join fetch f.user").getResultList();
    }

    // 모든 forum 검색
    public List<Forum> findAll(){
        return em.createQuery("select f from Forum f where f.deleted_status = false",Forum.class)
                .getResultList();
    }

    // script가 포함된 forum 찾기
    public List<Forum> findAllByType(String type){
        String jpql = "select f From Forum f ";
        jpql += " where f.deleted_status = false";
        System.out.println(type);
        System.out.println(type.equals("script"));
        if(type.equals("script".toString())){
            jpql += " and f.script_status = true";
        }else if(type.equals("interview")){
            jpql += " and f.interview_status = true";
        }else{
            jpql += " and f.script_status = false and f.interview_status = false";
        }
        return em.createQuery(jpql).getResultList();
    }

    public List<Forum> findAllByScript(){
        String jpql = "select f From Forum f ";
        jpql += " where f.deleted_status = :deleted_status";
        jpql += " and f.script_status = :script_status";

        TypedQuery<Forum> query = em.createQuery(jpql, Forum.class) .setMaxResults(100); //최대 1000건
        query = query.setParameter("deleted_status", false);
        query = query.setParameter("script_status",true);
        return query.getResultList();
    }

    // interview가 포함된 forum 찾기
    public List<Forum> findAllByInterview(){
        String jpql = "select f From Forum f ";
        jpql += " where f.deleted_status = :deleted_status";
        jpql += " and f.interview_status = :interview_status";

        TypedQuery<Forum> query = em.createQuery(jpql, Forum.class) .setMaxResults(100); //최대 1000건
        query = query.setParameter("deleted_status", false);
        query = query.setParameter("interview_status",true);
        return query.getResultList();
    }

    //script와 interview가 없는 forum 찾기
    public List<Forum> findAllByFree(){
        String jpql = "select f From Forum f ";
        jpql += " where f.deleted_status = :deleted_status";
        jpql += " and f.script_status = :script_status";
        jpql += " and f.interview_status = :interview_status";
        TypedQuery<Forum> query = em.createQuery(jpql, Forum.class) .setMaxResults(100); //최대 1000건
        query = query.setParameter("deleted_status", false);
        query = query.setParameter("script_status",false);
        query = query.setParameter("interview_status",false);
        return query.getResultList();
    }

    // 해당 유저의 모든 forum 찾기
    public List<Forum> findAllByUserId(ForumSearchById forumSearchById){
        String jpql = "select f From Forum f join f.user u";

        jpql += " where f.deleted_status = :deleted_status";
        // userId로 검색
        if (StringUtils.hasText(String.valueOf(forumSearchById.getUserId()))) {
            jpql += " and u.id = :id";
        }
        TypedQuery<Forum> query = em.createQuery(jpql, Forum.class) .setMaxResults(100); //최대 1000건

        query = query.setParameter("deleted_status", false);

        if (StringUtils.hasText(String.valueOf(forumSearchById.getUserId()))) {
            query = query.setParameter("id", forumSearchById.getUserId());
        }
        return query.getResultList();
    }

    public List<Forum> SearchAllByKeyword(String search_keyword){
        QForum qForum = QForum.forum;
        return jpaQueryFactory
                .selectFrom(qForum)
                .where(qForum.title.containsIgnoreCase(search_keyword)
                        .or(qForum.content.containsIgnoreCase(search_keyword)))
                .orderBy(qForum.like_num.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

}
