package com.manesculivia.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manesculivia.recipe.model.PantryIngredient;
import com.manesculivia.recipe.model.request.IngredientRequestDto;
import com.manesculivia.recipe.repository.PantryRepository;
import com.manesculivia.recipe.service.PantryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PantryController.class)
@Import(PantryService.class)
class PantryControllerTest {

    private static final String BASE_PATH = "/pantry/ingredients";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PantryService pantryService;

    @MockBean
    private PantryRepository pantryRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnForbidden_whenPerformingGetRequestWithRoleDifferentThanCook() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "COOK")
    void shouldReturnAlIngredientsFromPantry_whenPerformingGetRequest() throws Exception {
        // given
        PantryIngredient ingredient = new PantryIngredient();
        ingredient.setIngredientId(1);
        ingredient.setIngredientName("sugar");
        ingredient.setIngredientQuantity(2);
        given(pantryRepository.findAll()).willReturn(singletonList(ingredient));

        // when
        // then
        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].ingredientId").value(1))
                .andExpect(jsonPath("$.[0].ingredientName").value("sugar"))
                .andExpect(jsonPath("$.[0].ingredientQuantity").value(2));
    }

    @Test
    @WithMockUser(roles = "COOK")
    void shouldReturnCreatedIngredient_whenPerformingPostRequest() throws Exception {
        // given
        IngredientRequestDto ingredient = IngredientRequestDto.builder()
                .name("sugar")
                .quantity(2)
                .build();
        given(pantryRepository.findByIngredientNameIgnoreCase("sugar")).willReturn(empty());
        PantryIngredient createdIngredient = new PantryIngredient();
        createdIngredient.setIngredientId(1);
        createdIngredient.setIngredientName("sugar");
        createdIngredient.setIngredientQuantity(2);
        given(pantryRepository.save(any())).willReturn(createdIngredient);

        // when
        // then
        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ingredientId").value(1))
                .andExpect(jsonPath("$.ingredientName").value("sugar"))
                .andExpect(jsonPath("$.ingredientQuantity").value(2));
    }

}