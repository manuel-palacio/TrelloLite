package net.palacesoft.trellolite.stories;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ViewStoryTest {
    MockMvc mockMvc;

    @InjectMocks
    StoryQueriesController controller;

    @Mock
    StoryRepository storyRepository;

    List<Story> stories = asList(new Story("test", "test"));

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }


    @Test
    public void can_get_stories() throws Exception {


        when(storyRepository.findAll()).thenReturn(stories);

        this.mockMvc.perform(
                get("/resources/stories").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[*].title").value("test"))
                .andExpect(status().isOk());
    }
}
