(ns re-block.b4.progressbar
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(def precision 1000)
(defn- percentage
  [now _min _max]
  (-> (- now _min)
      (/ (- _max _min))
      (* 100)
      (* precision)
      js/Math.round
      (/ precision)))

(defn- render-progressbar
  [{:keys [min now max label
           sr-only striped active class style]
    :or {min 0 max 100
         active false sr-only false
         striped false} :as props} & children]
  (let [props (dissoc props  :min :now :max :label
                      :sr-only :striped :active :class :style)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              (u/prefix (select-keys bsp [:bs-class] ) )
              (u/prefix {:bs-class "bg"} (:bs-style bsp))
              {:active active
               (u/prefix bsp "striped") (or active striped)}
              class)]
    (into [:div (merge eps
                       {:class clss
                        :role "progressbar"
                        :style (assoc style :width (str (percentage now min max) "%"))
                        :aria-valuenow now
                        :aria-valuemin min
                        :aria-valuemax max})
           (if sr-only [:span {:class "sr-only"} label ]
               label)] children)))

(defn -progressbar
  [props & children ]
  (if (:is-child props)
    (apply render-progressbar props children)
    (let [[props children] (if (map? props)
                             [props children]
                             [{} (into [props]  children)])
          {:keys [min now max label
                  sr-only striped active class
                  bs-style bs-class]
           :or {min 0 max 100
                active false sr-only false
                striped false}} props
          props (dissoc props :min :now :max :label
                        :sr-only :striped :active :class
                        :bs-style :bs-class)
          [bsp eps] (u/split-bs-props props)]
      [:div (assoc props :class (uu/class "progress" class))
       (if children
         (mapv #(uu/clone-element % {:is-child true}) children)
         (render-progressbar {:min min
                               :now now
                               :max max
                               :label label
                               :sr-only sr-only
                               :striped striped
                               :active active
                               :bs-class bs-class
                               :bs-style bs-style}))] )))

(def progressbar
  (with-meta
    (u/bs-style :primary
                (u/bs-class "progress-bar" -progressbar))
    {:display-name "ProgressBar"}))

