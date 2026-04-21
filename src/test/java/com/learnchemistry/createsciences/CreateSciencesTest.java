package com.learnchemistry.createsciences;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSciencesTest {
    @Test
    void exposesStableModIdentity() {
        assertEquals("create_sciences", CreateSciences.MOD_ID);
        assertEquals("Create: Sciences", CreateSciences.MOD_NAME);
    }
}
