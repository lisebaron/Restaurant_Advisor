<?php

namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Restaurant extends Model
{
    protected $table = 'restaurant';

    public function menu()
    {
        return $this->hasMany('App\Model\Menu');
    }
}
