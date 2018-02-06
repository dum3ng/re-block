(ns re-block.b4.carousel-caption
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -carousel-caption
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [tag class]
         :or {tag "div"}} props
        props (dissoc props :tag :class)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class (u/get-class-set bsp))
        ps (merge eps {:class clss})]
    (if (fn? tag)
      (apply tag ps children)
      (into [(keyword tag) ps ] children)) ))

(def carousel-caption
  (with-meta  (u/bs-class "carousel-caption" -carousel-caption)
    {:display-name "CarouselCaption"}))

