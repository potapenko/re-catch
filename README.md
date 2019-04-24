# re-catch

**A simple, reusable Reagent error boundary component for Reagent 0.8.1+ (React 16+).**

[![Clojars Project](https://img.shields.io/clojars/v/re-catch.svg)](https://clojars.org/re-catch)

React [v16](https://reactjs.org/blog/2017/09/26/react-v16.0.html) introduced the concept of [“error boundaries”](https://reactjs.org/docs/error-boundaries.html).

This component provides a simple and reusable wrapper that you can use to wrap around your components. Any rendering errors in your components hierarchy can then be gracefully handled.

![](https://raw.githubusercontent.com/potapenko/re-catch/master/resources/images/catch-example.png)


## Restrictions

Error boundaried catch only error on rendering phase.

Error boundaries do not catch errors for:

* Event handlers
* Asynchronous code


## Usage

Add to project.clj

```
[re-catch "0.1.2"]
```

Wrap components tree with `[rc/catch ...]`:

```
(ns re-catch.example
  (:require [re-catch.core :as rc]
            [reagent.core :as r]))

(defn line [num]
  (if-not (= num 3)
    [:h3  "line" (str num)]
    [:h3 (-> js/nil .helloNil)]))

(defn line-safe [num]
  [rc/catch
   [line num]])

(defn main-panel []
  (fn []
   [:div.p-4
    [line-safe 1]
    [line-safe 2]
    [line-safe 3] ;; error thrown
    [line-safe 4]
    [line-safe 5]]))
```

## License

License

Copyright © 2019 Eugene Potapenko

Distributed under the Eclipse Public License, the same as Clojure.

