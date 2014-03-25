package net.palacesoft.trellolite.stories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface StoryRepository extends MongoRepository<Story, String> {

    public List<Story> findById(String id);

}