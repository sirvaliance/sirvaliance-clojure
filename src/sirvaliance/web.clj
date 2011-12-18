(ns sirvaliance.web
  (:use ring.adapter.jetty))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world"})

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))

