<?php


namespace App;

use Illuminate\Support\Facades\DB;


class RestaurantDatabase
{
    function getRestaurants()
    {
        $Restaurants = DB::table("restaurant")->get();
        return $Restaurants;
    }

    function getUsers()
    {
        $Users = DB::table("users")->get();
        return $Users;
    }
}
