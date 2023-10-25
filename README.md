# Pattern Recognition API

#### Adding a new point to the space

<details>
 <summary><code>POST</code> <code><b>/point</b></code></summary>

##### Parameters

> | type              |  data type     | description               | example                                                           |
> |-------------------|---------------|-------------------------|-----------------------------------------------------------------------|
> | Request body      |      JSON      | A point   | { "x": 0, "y": 0 }  |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `{ "x": 0, "y": 0 }`                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            | |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:8080/point
> ```

</details>

------------------------------------------------------------------------------------------

#### Adding multiple points to the space

<details>
 <summary><code>POST</code> <code><b>/points</b></code></summary>

##### Parameters

> | type              |  data type     | description               | example                                                           |
> |-------------------|---------------|-------------------------|-----------------------------------------------------------------------|
> | Request body      |      JSON      | A list of points       | [ { "x": 0, "y": 0 }, { "x": 1, "y": 1 }, ...]  |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `[ { "x": 0, "y": 0 }, { "x": 1, "y": 1 }, ...]`                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            | |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:8080/points
> ```

</details>


------------------------------------------------------------------------------------------

#### Removing all points from the space

<details>
 <summary><code>DELETE</code> <code><b>/space</b></code></summary>

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | empty                                |

##### Example cURL

> ```javascript
>  curl -X DELETE -H "Content-Type: application/json" http://localhost:8080/space
> ```

</details>

------------------------------------------------------------------------------------------

#### Listing all points into the space

<details>
 <summary><code>GET</code> <code><b>/space</b></code></summary>


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `[ { "x": 0, "y": 0 }, { "x": 1, "y": 1 }, ...]`                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            | |

##### Example cURL

> ```javascript
>  curl -X GET -H "Content-Type: application/json"  http://localhost:8080/space
> ```

</details>

------------------------------------------------------------------------------------------

#### Listing all line segments passing through at least N points

<details>
 <summary><code>GET</code> <code><b>/lines/{points}</b></code></summary>


##### Parameters

> | type              |  data type     | description               | example                                                           |
> |-------------------|---------------|-------------------------|-----------------------------------------------------------------------|
> | Request parameter      |      Integer      | The number of points to intersect       | 5 |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | `[ { "left": { "x": 0, "y": 0 }, "right": { "x": 0, "y": 0 }, "intersections": [ { "x": 0, "y": 0 }, ... ] } ]`                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`
##### Example cURL

> ```javascript
>  curl -X GET -H "Content-Type: application/json"  http://localhost:8080/lines/3
> ```

</details>

For further details, see the Swagger doc: http://localhost:8080/swagger-ui/index.html