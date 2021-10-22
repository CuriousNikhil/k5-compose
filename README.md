# k5-compose
**k5-compose** is a sketchy port of P5.js for Jetpack Compose Desktop. That's why the name **K5**.  

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

Say for example you want to create something like this - 

| Moving Vehicle code | K5 Sketch |
|---|---|
| ![carbon](https://user-images.githubusercontent.com/16976114/138512721-c580b54e-dcff-46c4-8df6-f43ec4d081f6.png) | ![simplest-mover](https://user-images.githubusercontent.com/16976114/138512547-40b085d0-bd22-4786-aa84-a38a58fc0122.gif) |


## Few more examples...



