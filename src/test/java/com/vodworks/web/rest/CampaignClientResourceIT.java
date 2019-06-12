package com.vodworks.web.rest;

import com.vodworks.JhipsterDemoAppApp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the CampaignClientResource REST controller.
 *
 * @see CampaignClientResource
 */
@SpringBootTest(classes = JhipsterDemoAppApp.class)
public class CampaignClientResourceIT {

    private MockMvc restMockMvc;

    /*@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CampaignClientResource campaignClientResource = new CampaignClientResource(campaignService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(campaignClientResource)
            .build();
    }*/

    /**
     * Test get
     */
    @Test
    public void testGet() throws Exception {
        restMockMvc.perform(get("/api/campaign/get"))
            .andExpect(status().isOk());
    }
}
