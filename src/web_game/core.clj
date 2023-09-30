(ns web-game.core
  (:gen-class)
   (:require 
    [org.httpkit.server :as hk-server] 
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.util.response :refer [file-response
                                resource-response
                                content-type]] 
    [ring.middleware.resource :refer [wrap-resource]])
  (:import (java.util Date)))

(file-response "index.html" {:root "resources/public"})

(defroutes app2
  (GET "/index" [] "<h1>Hello World</h1>")
  (GET "/" []
    (file-response 
     "index.html" 
     {:root "resources/public"}))
  (route/not-found "<h1>Page not found</h1>"))


(defn handleEntry [req]
  (println "received request on entry: " req)
  (let [r (-> (resource-response "public/index.html")
              (content-type "text/html")
              )]
    (println "r is : " r)
    r))

(defn handleIndex [req]
  (println "received req" req) 
   "<h1>Hello World</h1>")

(defroutes webRoutes
  (GET "/index" [] handleIndex)
  (GET "/" [] handleEntry)
  ;(route/files "/")
  (route/not-found "<h1>Page not found</h1>"))

(defn addMiddleWare [routes]
  (-> routes
      (wrap-resource "resources/public")
      (wrap-resource "resources/public/assets")
      ;(wrap-content-type "text/html")
      (wrap-content-type)
      (wrap-not-modified)))

(def now (Date.))
now

(def serverApp
  (addMiddleWare webRoutes))

(defn start-my-server [app port]
  (hk-server/run-server app {:port port}))

(defn -main
  [& args] 
  (println "Init")
  (start-my-server webRoutes 3000))


