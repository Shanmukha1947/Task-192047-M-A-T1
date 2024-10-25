import javax.script.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class JavaScriptAsyncExample {

    public static void main(String[] args) throws ScriptException, ExecutionException, InterruptedException, NoSuchMethodException {
        // Create a JavaScript engine
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // Define the JavaScript callback function as a string
        String callbackFunction = "function callback(result) {\n" +
                "   java.lang.System.out.println('Callback called with result: ' + result);\n" +
                "}";

        // Evaluate the callback function in the JavaScript engine
        engine.eval(callbackFunction);

        // Define the asynchronous JavaScript function that uses the callback
        String asyncFunction = "function asynchronousOperation(callback) {\n" +
                "   setTimeout(function() {\n" +
                "       var result = 'Hello from JavaScript!';\n" +
                "       callback(result);\n" +
                "   }, 2000); // Simulate a 2-second delay\n" +
                "}";

        // Evaluate the asynchronous function in the JavaScript engine
        engine.eval(asyncFunction);

        // Call the asynchronous JavaScript function from Java using Invocable
        Invocable invocable = (Invocable) engine;

        // Update this line to correctly invoke the function
        invocable.invokeFunction("asynchronousOperation", invocable.getInterface(Runnable.class));

        // Use CompletableFuture to wait for the asynchronous operation to complete
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Java code doing work...");
        future.get(); // Wait for the future to complete
        System.out.println("Async operation completed.");
    }
}
