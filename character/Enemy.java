package character;

import java.util.Hashtable;

public abstract class Enemy extends TemplateCharacter {

    public Enemy(String name, Hashtable<String, Integer> attributes, int exp) {
        super(name, attributes);
        setEXP(exp);
    }   
}
