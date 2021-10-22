<?php

namespace App\Http\Controllers;

use App\Model\Menu;
use App\Model\Restaurant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class MenuController extends Controller
{

    public function getMenus($id)
    {
        $menu = Menu::where('restaurant_id', $id)->get();
        return response()->json($menu, 200);
    }

    public function create(Request $request, $id)
    {
        $menu = Menu::where('restaurant_id', $id)->get();

        $menu = new Menu();

        $menu->name = $request->input('name');
        $menu->description = $request->input('description');
        $menu->price = $request->input('price');
        $menu->restaurant_id = $request->input('restaurant_id');

        $menu->save();
        if ($menu == null) {
            return response(null, 400);
        } else {
            return response()->json($menu, 201);
        }
    }

    public function modify(Request $request, $id, $menu_id)
    {
        $menu = Menu::where('restaurant_id', $id);
        $menu = $menu->where('id', $menu_id)->first();

        $menu->name=$request->name;
        $menu->description=$request->description;
        $menu->price=$request->price;

        $menu->save();
        if ($menu == null) {
            return response(null, 400);
        }
    }

    public function delete(Request $request, $id, $menu_id)
    {
        $menu = Menu::where('restaurant_id', $id);
        $menu = $menu->where('id', $menu_id)->first();

        $menu->delete();
        if ($menu == null){
            return response(null, 400);
        }else {
            return response()->json(null, 200);
        }
    }

}
