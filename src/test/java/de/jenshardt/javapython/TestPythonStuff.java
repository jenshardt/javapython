package de.jenshardt.javapython;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Test;
import org.python.util.PythonInterpreter;

public class TestPythonStuff {

    @Test
    public void UsingScriptEngine() throws Exception {
        StringWriter output = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(output);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("python");
        engine.eval(new FileReader(resolvePythonScriptPath("hello.py")), context);
        assertEquals("Hello strange world!", output.toString().trim());
    }

    @Test
    public void UsingJython() {
        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            pyInterp.setOut(output);

            pyInterp.exec("print('Hello strange world!')");
            assertEquals("Hello strange world!", output.toString().trim());
        }
    }

    @Test
    public void UsingApache() throws ExecuteException, IOException {
        String line = "python " + resolvePythonScriptPath("hello.py");
        CommandLine cmdLine = CommandLine.parse(line);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);

        int exitCode = executor.execute(cmdLine);
        assertEquals(0, exitCode);
        assertEquals("Hello strange world!", outputStream.toString()
                .trim());
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File("src/main/resources/" + filename);
        return file.getAbsolutePath();
    }
}