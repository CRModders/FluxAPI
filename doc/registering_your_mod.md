# Registering your Mod with Flux API
To register your mod with Flux API you need to register a couple of methods
with it. an example mod could look like this:
```java
public class ExampleMod implements ModInitializer {

    public static String MOD_ID = "examplemod";
    public static Identifier MOD_NAME = new Identifier(MOD_ID, "name");

    @Override
    public void onInitialize() {
        FluxRegistries.EVENT_BUS.register(this);
        FluxRegistries.ON_INITIALIZE.register(MOD_NAME, this::onInit);
    }

    public void onInit() {
        ExampleBlockEntity.register();
    }

    @Subscribe
    public void onEvent(OnRegisterBlockEvent event) {
        event.registerBlock(() -> new DataModBlock("diamond_block", new ResourceLocation(MOD_ID, "blocks/diamond_block.json")));
        event.registerBlock(Bedrock::new);
    }

    @Subscribe
    public void onEvent(OnRegisterLanguageEvent event) {
        event.registerLanguage(new ResourceLocation(MOD_ID, "languages/en-US.json").load());
    }

}
```
we have our main entrypoint `onInitialize` in which we register our mod as an event subscriber and an
`onInit` method. Flux APIs EventBus registers our mod to `OnRegisterBlockEvent` and `OnRegisterLanguageEvent`,
we also hook into the mod load cycle using `onInit`. there also exists `FluxRegistries#ON_PRE_INITIALIZE`
and `FluxRegistries#ON_POST_INITIALIZE`