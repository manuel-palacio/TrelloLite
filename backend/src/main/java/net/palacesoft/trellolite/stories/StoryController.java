package net.palacesoft.trellolite.stories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoryController {


    private StoryRepository storyRepository;

    @Autowired
    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
        storyRepository.save(new Story("Alice", "Smith"));
    }

    @RequestMapping("/stories")
    @ResponseBody
    public List<Story> stories() {
        return storyRepository.findAll();
    }

    public void save(Story story){

    }

}
