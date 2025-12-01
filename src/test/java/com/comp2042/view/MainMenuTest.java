package com.comp2042.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * author tooba
 * Basic tests for MainMenu without JavaFX initialization.
 */
@DisplayName("MainMenu Basic Tests")
public class MainMenuTest {

    @Test
    @DisplayName("Test MainMenu class exists and is loadable")
    void testMainMenuClassExists() {
        assertDoesNotThrow(() -> {
            Class.forName("com.comp2042.view.MainMenu");
        }, "MainMenu class should be loadable");
    }

    @Test
    @DisplayName("Test MainMenu package structure")
    void testPackageStructure() {
        String expectedPackage = "com.comp2042.view";
        try {
            Class<?> mainMenuClass = Class.forName("com.comp2042.view.MainMenu");
            assertEquals(expectedPackage, mainMenuClass.getPackage().getName(),
                    "MainMenu should be in the correct package");
        } catch (ClassNotFoundException e) {
            fail("MainMenu class not found");
        }
    }

    @Test
    @DisplayName("Test MainMenu has no compilation errors")
    void testMainMenuCompiles() {
        // This test passes if the class compiles and loads
        assertDoesNotThrow(() -> {
            ClassLoader.getSystemClassLoader().loadClass("com.comp2042.view.MainMenu");
        }, "MainMenu should compile and load without errors");
    }
}