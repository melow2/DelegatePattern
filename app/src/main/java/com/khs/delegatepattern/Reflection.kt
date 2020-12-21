package com.khs.delegatepattern


fun isEven(a:Int) = a %2==0
fun length(str:String) = str.length
fun <A,B,C> compose(f:(B) -> C,g:(A) ->B):(A)->C{
    return { x -> f(g(x))}
}