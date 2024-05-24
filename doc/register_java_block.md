# Registering a Java Block
Registering a block in Flux API is a simple task, first we create
a class that implements `IModBlock`, we create a constructor that accepts 
our blocks id and name. `IModBlock` requires us to implement `getBlockGenerator`
which is responsible for creating out block states. the constructor should be replaced
with static constants, but that part is omitted here.
```java

public class MyBlock implements IModBlock {

    public Identifier blockId;
    public String blockName;

    public MyBlock(Identifier blockId, String blockName) {
        this.blockId = blockId;
        this.blockName = blockName;
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        BlockGenerator generator = new BlockGenerator(blockId, blockName);
        return generator;
    }
    
}
```
## Creating Block Models
now we can add a default block state that references a default `cube` model, we
specify our model name `cube` and tell the block generator that we use a custom block
model generator by adding `true` to the method call. <br>
if we want to load a data block model, omit the true flag and specify a valid data block
model path instead of `cube`
```java
public BlockGenerator getBlockGenerator() {
    BlockGenerator generator = new BlockGenerator(blockId, blockName);
    generator.createBlockState("default", "cube", true);
    return generator;
}
```
after creating a block state that references a *custom* block model, we need to create
one. for that we have to implement `getBlockModelGenerators` which returns a list of `BlockModelGenerator`
each having its own name. a `BlockModelGenerator` needs the block id passed into the generator method to
create a unique model name to avoid name collisions, this is handled automatically
```java
public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
    BlockModelGenerator generator = new BlockModelGenerator(blockId, "cube");
    return List.of(generator);
}
```
now we have to construct the shape of our `cube` block model, to do this we have to create
a texture and a cuboid in our `BlockModelGenerator` using `createTexture` and `createCuboid` 
respectively. to create a basic cube model we create an `all` texture and a cuboid with
a solid block as its shape. the cuboid references the `all` texture
```java
public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
    BlockModelGenerator generator = new BlockModelGenerator(blockId, "cube");
    generator.createTexture("all", /* a Pixmap or ResourceLocation */ );
    generator.createCuboid(0, 0, 0, 16, 16, 16, "all");
    return List.of(generator);
}
```
## Adding Block Events and Actions
FluxAPI provides 3 overrides for custom block actions: `onPlace`, `onBreak` and `onInteract`.
when overriding these in your `IModBlock` you can omit the super call to override default
game behavior, such as not breaking the block. <br>
for adding java block events we need to override the `getBlockEventGenerators` method from `IModBlock`
```java
public List<BlockEventGenerator> getBlockEventGenerators(Identifier blockId) {  
    BlockEventGenerator generator = new BlockEventGenerator(blockId, "events");
    generator.createTrigger("do_something", Identifier.fromString("base:run_trigger"), Map.of(/* parameters here */));  
    return List.of(generator);  
}
```
to use this custom event we need to add some extra parameters to `createBlockState`
```java
public BlockGenerator getBlockGenerator() {
    BlockGenerator generator = new BlockGenerator(blockId, blockName);
    generator.createBlockState("default", "cube", true, "events", true);
    return generator;
}
```
again if we want to use data block events we need to specify false after "events".<br>
**for registering custom block *actions* refer to *Registering Java Block Actions***
# Adding your Block to the Game
to add your java block to the game, you need to register an event listener, preferably in
your mod init class, you need to listen to `OnRegisterBlockEvent` and register a factory method
```java
public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        FluxRegistries.EVENT_BUS.register(this);
    }

    @Subscribe
    public void onEvent(OnRegisterBlockEvent event) {
        event.registerBlock(() -> new MyBlock(/* ... */));
    }

}
```
**Congratulations you have added a java-only block to Cosmic Reach**