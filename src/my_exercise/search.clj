(ns my-exercise.search
  (:require [clojure.string :as st]))

(defonce ocd-base "ocd-division/country:us")

(defn submit [request]
  (html5 (str (ocd-ids (form-params request)))))

(defn form-params [request]
  (get request :form-params))


(defn format-val [prepend getter address]
  (-> address
      (get getter)
      (st/lower-case)
      (st/replace #" " "_")
      (#(str prepend %))))

(def get-state
  (partial format-val "state:" "state"))

(def get-place
  (partial format-val "place:" "city"))

(defn ocd-modifiers [address]
  [""
   (get-state address)
   (get-place address)])

(defn ocd-ids [address]
  (vec (map #(st/join "/" [ocd-base %]) (ocd-modifiers address))))



;; ocd-division/country:us
;; ocd-division/country:us/state:or
;; ocd-division/country:us/state:or/county:multnomah
;; ocd-division/country:us/state:or/place:portland 
