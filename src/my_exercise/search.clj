(ns my-exercise.search
  (:require [clojure.string :as st]
            [clj-http.client :as client]))

(defn address [request]
  (get request :form-params))

(defn format-val [prepend getter address]
  "Formats the parameters from the form.
  Can add other replacements if form input varies."
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
  "List of all modifiers to the ocd-base.
  Can add others, like county, legislative districts, etc.  "
  [""
   (get-state address)
   (get-place address)])

(defn ocd-ids [address]
  "Joins all ocd-ids together in a vector."
  (vec (map #(st/join "/" [ocd-base %])
            (ocd-modifiers address))))

(defn get-election-data [ocd-ids]
  "Gets election data from TurboVote API."
  (let [get-base "https://api.turbovote.org/elections/upcoming?district-divisions="
        arg-str (st/join "," ocd-ids)]
    (client/get (str get-base arg-str))))

(defn submit [request]
  (-> request
      (address)
      (ocd-ids)
      str

      ;; This is commented out, so the request doesn't hang
      ;; (get-election-data)

      ;; TODO Reder a page of Elections
      ))

