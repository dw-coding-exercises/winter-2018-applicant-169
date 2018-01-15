(ns my-exercise.search
  (:require [clojure.string :as st]
            [clj-http.client :as client]))

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

(defonce ocd-base "ocd-division/country:us")

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


(defn get-election-data [ocd-ids]
  (let [get-base "https://api.turbovote.org/elections/upcoming?district-divisions="
        arg-str (st/join "," ocd-ids)]
    (client/get (str get-base arg-str))))

; curl 'https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:or,ocd-division/country:us/state:or/place:portland'

