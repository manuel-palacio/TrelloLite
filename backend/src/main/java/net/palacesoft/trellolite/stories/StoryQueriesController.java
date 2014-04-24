package net.palacesoft.trellolite.stories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources/stories")
public class StoryQueriesController {

    private StoryRepository storyRepository;

    @Autowired
    public StoryQueriesController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Story> stories() {
        return storyRepository.findAll();
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.GET)
    public ResponseEntity<Story> story(@PathVariable("storyId") String id) {
        Story story = storyRepository.findById(id);
        if (story == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

}
