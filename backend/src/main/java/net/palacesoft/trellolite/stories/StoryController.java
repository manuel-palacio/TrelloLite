package net.palacesoft.trellolite.stories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/resources/stories")
public class StoryController {

    private StoryRepository storyRepository;

    @Autowired
    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
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

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Story> update(@RequestBody Story story) {
        storyRepository.save(story);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Story story, HttpServletRequest request, HttpServletResponse response) {
        storyRepository.save(story);
        response.setHeader("Location", request.getRequestURL().append("/").append(story.getId()).toString());
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("storyId") String id) {
        storyRepository.delete(id);
    }

}
