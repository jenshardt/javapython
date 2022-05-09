package de.jenshardt.javapython;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.junit.jupiter.api.Test;

public class TestPythonStuff {

    @Test
    public void givenPythonScriptEngineIsAvailable_whenScriptInvoked_thenOutputDisplayed() throws Exception {
        StringWriter output = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(output);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("python");
        engine.eval(new FileReader(resolvePythonScriptPath("hello.py")), context);
        assertEquals("Should contain script output: ", "Hello Baeldung Readers!!", output.toString()
            .trim());
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File("src/main/resources/" + filename);
        return file.getAbsolutePath();
    }
}