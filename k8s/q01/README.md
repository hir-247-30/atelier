```
# ダッシュボード表示
# ServiceAccountとSecretの作成
kubectl apply -f dashboard-adminuser.yaml
kubectl apply -f dashboard-adminuser-secret.yaml

# Secrets取得
kubectl -n kubernetes-dashboard create token admin-user

# ダッシュボードを起動
kubectl proxy

# http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/#/error?namespace=default にアクセス
# 取得したSecretsを使ってログイン

# 後片付け
kubectl delete -f dashboard-adminuser.yaml
kubectl delete -f dashboard-adminuser-secret.yaml
```