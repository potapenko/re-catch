(ns reagent-catch.core
  (:require  [reagent.core :as r]))

(defn catch [{:keys [title show-stack?]}]
  (let [error-state (r/atom nil)
        info-state  (r/atom nil)]
    (r/create-class
     {:component-did-catch
      (fn [this error info]
        (reset! error-state (str error ))
        (reset! info-state (-> info .-componentStack)))
      :reagent-render (fn [& body]
                        (if @error-state
                          [:div
                           {:style {:width "100%"
                                    :backgroundColor "rgba(255,0,0,0.2)"
                                    :padding 8}}
                           (when title
                             [:h3 title])
                           [:h6 @error-state]
                           (when show-stack?
                             [:pre @info-state])]
                          (into [:<>] body)))})))
