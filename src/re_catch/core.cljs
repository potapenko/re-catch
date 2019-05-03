(ns re-catch.core
  (:require  [reagent.core :as r]
             [clojure.string :as string]))

(defn- render-error [{:keys [error info]}]
  [:div
   {:style {:width           "100%"
            :min-width       300
            :backgroundColor "rgba(255,0,0,0.2)"
            :padding         8}}
   [:h6 error]
   [:pre info]])

(defn catch [{:keys [render-error-component]
              :or   {render-error-component render-error}}]
  (let [error-state (r/atom nil)
        info-state  (r/atom nil)]
    (r/create-class
     {:component-did-catch
      (fn re-catch-block [this error info]
        (reset! error-state (let [max-error-lenght 200]
                              (when-let [err (str error)]
                                (if (-> err count (< max-error-lenght))
                                  (-> err
                                      (subs 0 max-error-lenght)
                                      (str " ..."))))))
        (reset! info-state (some->> info
                                    .-componentStack
                                    (string/split-lines)
                                    (remove string/blank?)
                                    (drop-while #(re-find #"re_catch" %))
                                    (take 3)
                                    (string/join "\n"))))
      :reagent-render (fn [& body]
                        (if @error-state
                          [render-error-component
                           {:error @error-state :info @info-state}]
                          (into [:<>] body)))})))
