<?php

namespace App\Http\Controllers;


use App\Model\RestaurantDatabase;
use App\Model\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class UsersController extends Controller
{
    public function getUsers()
    {
        return Response::json(User::all(), 200);
    }

    public function register(Request $request)
    {
        $user = new User();

        $user->login = $request->input('login');
        $user->password = $request->input('password');
        $user->email = $request->input('email');
        $user->firstname = $request->input('firstname');
        $user->name = $request->input('name');
        $user->age = $request->input('age');

        $user->save();

        if ($user == null) {
            return response(null, 400);
        } else {
            return response()->json($user, 201);
        }
    }

    public function auth(Request $request)
    {
        $login = $request->login;
        $password = $request->password;
        $user = User::all()->where('login', $login)->first();
        if ($user != null)
            if ($password == $user->password)
                return response()->noContent(200);

        return response()->noContent(400);
    }
}
