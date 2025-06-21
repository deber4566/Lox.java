package lox.lox;

import java.util.HashMap;
import java.util.Map;

// this is where variables are housed

class Environment {
    final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    //global scope environment
    Environment() {
        enclosing = null;
    }

    //local scope environment
    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    //retrieve a variable
    Object get(Token name) {
        //find variable inside global scope
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }

        //if not found in global, we search local
        if (enclosing != null) {
            return enclosing.get(name);
        }

        //not found in local or global then we return an error
        throw new RuntimeError(name,
                "Undefinied variable '" + name.lexeme + "'.");
    }

    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name,
                "Undefined variable  '" + name.lexeme + ".");
    }

    // dont have to check if key is already in. this is due to variables being able to be redefined.
    void define(String name, Object value) {
        values.put(name, value);
    }
}
