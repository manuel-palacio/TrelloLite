package net.palacesoft.trellolite.stories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private StoryRepository storyRepository;

    @Autowired
    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Story[] stories() {
        List<Story> all = storyRepository.findAll();
        return all.toArray(new Story[all.size()]);
    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.GET)
    public Story[] story(@PathVariable("storyId") String id, HttpServletResponse response) {
        List<Story> all = storyRepository.findById(id);
        if(all.isEmpty()) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return all.toArray(new Story[all.size()]);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(Story story, HttpServletRequest request, HttpServletResponse response) {
        storyRepository.save(story);
        response.setHeader("Location", request.getRequestURL().append("/").append(story.getId()).toString());

    }

    @RequestMapping(value = "/{storyId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("storyId") String id) {
        storyRepository.delete(id);
    }

}
