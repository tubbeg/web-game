(ns web-game.core
  (:gen-class)
   (:require 
    [org.httpkit.server :as hk-server] 
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.util.response :refer [file-response]] 
    [ring.middleware.resource :as resource])
  (:import (java.util Date)))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello there :) :)"})

(file-response "index.html" {:root "resources/public"})

(defroutes app2
  (GET "/index" [] "<h1>Hello World</h1>")
  (GET "/" [] (file-response  "index.html" {:root "resources/public"}))
  (route/not-found "<h1>Page not found</h1>"))

(defroutes webRoutes
  (GET "/index" [] "<h1>Hello World</h1>")
  (GET "/" [] (file-response  "index.html" )); {:root "resources/public"}))
  (route/not-found "<h1>Page not found</h1>"))

(defn addMiddleWare []
  (resource/wrap-resource webRoutes "public"))


(def now (Date.))
now


(def serverApp
  (addMiddleWare))

(defn start-my-server [app]
  (hk-server/run-server app {:port 3000}))

(defn -main
  [& args] 
  (println "Init")
  (start-my-server serverApp))

