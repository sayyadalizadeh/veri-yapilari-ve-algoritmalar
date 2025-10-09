# Veri Yapıları ve Algoritmalar Kodları

Bu depo, **Veri Yapıları ve Algoritmalar** dersine ait tüm örnek kodları içermektedir. Amaç, öğrenciler ve ilgilenenler için tüm pratik örnekleri ve alıştırmaları tek bir yerde toplamak ve düzenli bir şekilde sunmaktır.

---

## Depo Yapısı

Her ders konusu, ayrı bir klasörde saklanır. Klasör adları, konuyu açık bir şekilde belirtir.
```
Root/
│
├─ Arrays_Java/ # Dizilerle ilgili örnekler
│ ├─ ArrayExample.java
│ ├─ ArrayUpdate.java
│ └─ ...
│
├─ Lists/ # Listeler ile ilgili örnekler (tek yönlü, çift yönlü, dairesel ve işlemleri)
│ ├─ SinglyLinkedList.java
│ ├─ DoublyLinkedList.java
│ ├─ CircularSinglyLinkedList.java
│ ├─ CircularDoublyLinkedList.java
│ └─ Operations/ # Ekleme, silme, arama, toplam ve ortalama işlemleri
│ ├─ SinglyLinkedListOperations.java
│ ├─ DoublyLinkedListOperations.java
│ └─ ...
│
├─ Queues/ # Gelecek: Kuyruk yapıları örnekleri
├─ Stacks/ # Gelecek: Yığın yapıları örnekleri
└─ README.md # Bu rehber
```

> Her klasör, o konudaki alıştırmalar ve örnekler tamamlandığında güncellenecektir.

---

## Genel Kurallar

1. **Klasör İsimlendirmesi:**  
   - Her konu, bağımsız bir klasör içinde ve anlaşılır isimle yazılmıştır.  
   - Örnek: `Arrays_Java`, `Lists`, `Stacks`, `Queues`.

2. **Dosya İsimlendirmesi:**  
   - Dosya isimleri, içerdiği sınıf veya programla uyumludur.  
   - Örnek: `SinglyLinkedList.java`, `ArrayExample.java`.

3. **`.gitignore` Kullanımı:**  
   - Geçici dosyalar, derlenmiş sınıflar (`*.class`) ve IDE ayarları depoya eklenmemiştir.  
   - Örnek `.gitignore` dosyası:
     ```
     *.class
     .vscode/
     *.log
     ```

4. **Güncellemeler:**  
   - Her konu tamamlandığında veya yeni örnek eklendiğinde, net bir commit mesajı ile commit atılmaktadır:
---

## Kullanıcılar İçin Öneriler

- Pull veya push işleminden önce her zaman durumu kontrol edin:
  ```bash
  git status
