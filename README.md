# k5-compose
**k5-compose** is a sketchy port of [P5.js](https://p5js.org/) for Jetpack Compose Desktop.

This library provides you a playground to play with your sketches so you don't have to worry about maintaining & remember `States`, animations etc.
You can focus on creating awesome sketches, creating generative art. This library also provides you necessary physics and math functions which are ported from p5.js. 

In order to understand using this library for creating experiments, I would recommend to go through the Nature of Code book by Daniel Shiffman - https://natureofcode.com/book/.
This will give you the overall knowledge about how a physics system works in simplest way in p5/k5 or in general computer world.  
However, you could start digging into it right away by checking examples.

## Getting started

1. Create your [Jetpack Compose Desktop](https://github.com/JetBrains/compose-jb) project. Add the following dependency in your `build.gradle` along with the `compose` dependencies

```kotlin
implementation("me.nikhilchaudhari:k5-compose:{latest-version}")
```

2. Use `k5` dsl construct to create K5 sketch

```kotlin
fun main() = k5{

  // Initialise your variables, objects

  show { drawScope ->
    // use drawScope to draw the shapes
  }  
}
```

3. Use [library apis](https://javadoc.io/doc/me.nikhilchaudhari/k5-compose/latest/k5-compose/math/index.html) for calculations and you are good to go! :p

Say for example you want to create something like this with 20 lines of code - 

| Moving Vehicle code | K5 Sketch |
|---|---|
| ![carbon](https://user-images.githubusercontent.com/16976114/138512721-c580b54e-dcff-46c4-8df6-f43ec4d081f6.png) | ![fastest_gif](https://user-images.githubusercontent.com/16976114/138569158-ac14af91-d9ed-48c4-bd51-65e48a8c71cd.gif) |

No need to manage/remember states, animation or anything. Just focus on your logic to design sketch rest of the things are handled by the library.

## Few more examples...

| [parametric eq](https://github.com/CuriousNikhil/k5-compose/blob/main/src/main/kotlin/examples/mathematics/parametric-equation.kt) | [particles js](https://github.com/CuriousNikhil/k5-compose/blob/main/src/main/kotlin/examples/simulations/particles-js-simulation.kt)  | [gravitation](https://github.com/CuriousNikhil/k5-compose/blob/main/src/main/kotlin/examples/forces/GravitationalAttraction.kt)  | [game of life automaton](https://github.com/CuriousNikhil/k5-compose/blob/main/src/main/kotlin/examples/simulations/gameOfLife.kt) |
|---|---|---|---|
| <video width="500" height="500" style="width: 500px; height: 500px;" src="https://user-images.githubusercontent.com/16976114/138568858-10628d40-05f9-4d0a-94a5-d5068c94c6dc.mov" autoplay muted loop /> | <video width="500" height="500" src="https://user-images.githubusercontent.com/16976114/138568867-1842d1d7-3669-4e38-851f-75a24824f05f.mov" autoplay muted loop /> | <video width="500" height="500" style="width: 500px; height: 500px;" src="https://user-images.githubusercontent.com/16976114/138568878-7c9b5fef-e0cc-4983-9553-319c5b233ec4.mov" autoplay muted /> | <video width="500" height="500" style="width: 500px; height: 500px;" src="https://user-images.githubusercontent.com/16976114/138568885-b01a4749-89ca-439e-94a0-5bb1cae217a3.mov" autoplay muted /> |


Click on the link to go to the code. Code explains the things in details. Try playing with those by tweaking values and running on your own. 😄 (I have added videos instead of gifs just so you can view these precisely without loosing any frames 😉)


## How do I do that?

### k5

It's very easy, you have to use `k5{...}` builder in order to initialise your k5-sketch. The things you define in here will be initialised only once.

```kotlin
fun main() = k5{
  // you can define all your properties, variables, vectors here.
    val position = Vector2D(20f, 20f)
    
    // Say you want to have a control over your shape and few other parameters like, force, acceleration, mass etc. You can use classes to represent your shapes.
    val spaceShip = SpaceShip(position, size) // some data class representing spaceship
    
  //...
}
```

You can pass `size` param in `k5()` 

```kotlin
  fun main() = k5(size = Size(800f, 500f)) { ... }
```

And there are more number of configurations you can do to k5-compose playground which will be applied to the `Window{..}` composable. You can check API docs.
// TODO - api docs.


### show

Once you have initialised your necessary stuff which is going to change while running the frame of animation, you have to draw your shape/sketch in the `show{...}` function. `show` function gives you a canvas drawscope which you can used to draw into the k5 compose playground. 

Note: Whatever you pass in `show{...}` lambda will run for every frame. So if you want to update the values you've initialised and draw the sketch every frame you can pass those things in the lambda. For example -

```kotlin
fun main() = k5{
  val vehiclePosition = Vector2D(20f, 20f)

  show{ drawScope ->
      
      //update your vehicle position, 
      vehiclePosition.add(Vector2D.randomVector() * 2f)
      
       drawScope.drawCircle(color = Color.White, radius = 30f, center = vehiclePosition.toOffSet())
  }
}
```

You can apply all the compose `Modifiers` to the playground like changing background, color and taking keyboard and mouse pointer input. 

```kotlin
    show(modifier = Modifier
        .background(Color.Yellow)
        .pointerMoveFilter(
            onMove = {
                //use mouse pointer values here
                false
            }
        )
    ) {
        // Draw your sketch
    }
```


### Few handy Apis

**noLoop**

Here, the `vehiclePosition` is constantly updated by adding a random vector to it's previous position and then a new circle is drawn on the playground based on the updated position. Simple, right?

Let's say you don't want to keep your sketch in loop. Let's say you want to draw it only once. You can use `noLoop()` method in `k5{...}`.

**Playground size**

You can use `dimensInt` or `dimensFloat` properties available in `k5{...}` to get the size of the playground. You can pass the `size` in `k5()` as well but there are few density display metrics related issues in using floating point values. So to avoid any miscalculations, these `Size` values can be used to get the precise height and width of your playground.


```kotlin
fun main() = k5 {

    // Use noLoop to draw your content only once
    noLoop()

    show {
        // this will be drawn only once
    }

}
```


## How do I use math and physics functions?

To use and understand mathematics and physics I would recommend [Nature Of Code](https://natureofcode.com/book/) book and [Video series](https://www.youtube.com/watch?v=70MQ-FugwbI&list=PLRqwX-V7Uu6ZV4yEcW3uDwOgGXKUUsPOM) by Daniel Shiffman. Or you can go through the [examples](https://github.com/CuriousNikhil/k5-compose/tree/main/src/main/kotlin/examples) section in the repo.

### Vectors

### Noise

### Trigonometry

### Calculations

### Random





