<?php

namespace App\Http\Controllers;

use App\Model\Restaurant;
use Illuminate\Http\Request;
use App\Model\RestaurantDatabase;
use Illuminate\Support\Facades\Response;

class RestaurantController extends Controller
{
    public function getRestaurants() {
        return Response::json(Restaurant::all(), 200);
    }

    public function create(Request $request)
    {
        $restaurant = new Restaurant();

        $restaurant->name = $request->input('name');
        $restaurant->description = $request->input('description');
        $restaurant->grade = $request->input('grade');
        $restaurant->localization = $request->input('localization');
        $restaurant->phone_number = $request->input('phone_number');
        $restaurant->website = $request->input('website');
        $restaurant->hours = $request->input('hours');

        $restaurant->save();

        if ($restaurant == null){
            return response(null, 400);
        } else {
            return response()->json($restaurant, 201);
        }
    }

    public function modify(Request $request)
    {
        $restaurant = Restaurant::find($request->id);
        $restaurant->name=$request->name;
        $restaurant->description=$request->description;
        $restaurant->grade=$request->grade;
        $restaurant->localization=$request->localization;
        $restaurant->phone_number=$request->phone_number;
        $restaurant->website=$request->website;
        $restaurant->hours=$request->hours;

        $restaurant->save();

        if ($restaurant == null) {
            return response(null, 400);
        }
    }

    public function delete(Request $request, Restaurant $restaurant)
    {
        $restaurant = Restaurant::find($request->id);
        $restaurant->delete();

        if ($restaurant == null) {
            return response(null, 400);
        } else {
            return response()->json(null, 200);
        }

    }
}
