# Adding Custom UI
*Note Flux API implements its own UI framework that is separate from the base game.*<br>
Flux API provides Classes that help you add custom UI, such as BasicMenu for creating a
GameState that can display UI Elements and a generic Component UI container. Flux API
includes many UI components that deal with common task, in addition all UI components provide
translation options.
## Creating a Basic Menu
To create a basic menu we first have to create a class extending BasicMenu, optionally
you can override a constructor that accepts a GameState for adding back buttons and so on, otherwise
use the default constructor. In this example we want to have a previous GameState
```java
public class Test extends BasicMenu {

    public Test(GameState previousState) {
        super(previousState);
    }

}
```
Now we have to create and add UI components, to do this we override `create` from GameState
```java

```
TODO