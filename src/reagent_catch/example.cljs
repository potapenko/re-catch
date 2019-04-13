(ns reagent-catch.example
  (:require [reagent-catch.core :as rc]
            [reagent.core :as r]))

(defn line [num]
  (if-not (= num 3)
    [:h1 (str num) " line"]
    [:h1 (-> js/nil .helloNil)]))

(defn line-safe [num]
  [rc/catch {:show-stack? false}
   [line num]])

(defn main-panel []
  (fn []
   [:div.p-4
    [line-safe 1]
    [line-safe 2]
    [line-safe 3]
    [line-safe 4]
    [line-safe 5]]))

(defn init! []
  (r/render [main-panel] (.getElementById js/document "app")))

(init!)
