(ns re-block.b4.card
  (:require [re-block.b4.utils :as u]))



(defn card-title
  [props & children]
  (if (map? props)
    (let [{:keys [tag-name] :or {tag-name :h5} } props]
      (into [tag-name (u/update-props-class props "card-title")] children))
    (into [:h5 props] children)))

(defn card-text
  [props & children]
  (into [:p.card-text props] children))

  (def image-placeholder "http://diaryofasocialgal.com/wp-content/uploads/2016/03/placeholder.png")
(defn card-image
  ([]
   (card-image {}))
  ([{:keys [location ] :or {location :top } :as props}]
   (let [cls (str "card-img-" (name location))
         props (u/update-props-class props cls)]
     [:img (merge {:src image-placeholder} props) ])))

(defn card-body
  [props & children]
  (into [:div.card-body props] children))

(defn card
  [props & children]
  (into [:div.card props] children))


;; <div class="card" style="width: 18rem;">
;; <img class="card-img-top" src="..." alt="Card image cap">
;; <div class="card-body">
;; <h5 class="card-title">Card title</h5>
;; <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
;; </div>
;; <ul class="list-group list-group-flush">
;; <li class="list-group-item">Cras justo odio</li>
;; <li class="list-group-item">Dapibus ac facilisis in</li>
;; <li class="list-group-item">Vestibulum at eros</li>
;; </ul>
;; <div class="card-body">
;; <a href="#" class="card-link">Card link</a>
;; <a href="#" class="card-link">Another link</a>
;; </div>
;; </div>
