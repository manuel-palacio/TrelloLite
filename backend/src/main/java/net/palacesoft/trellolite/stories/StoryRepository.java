package net.palacesoft.trellolite.stories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface StoryRepository extends MongoRepository<Story, String> {

    public Story findByName(String firstName);
    public List<Story> findByDescription(String lastName);
    public List<Story> findById(String id);

}