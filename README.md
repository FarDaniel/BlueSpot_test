# BlueSpot_test

## A projekt során meghozott fontosabb döntések leírása:

- A tableteket úgy határoztam meg, hogy 600dp-nél szélesebb a kijelzője.
- A kért API szint már nem támogatott, így magasabb szintű, de 90%-nál magasabb esztimált lefedettségű API-jal dolgoztam.
- Backend-den a képek nagyobb verziói máshogy croppoltak, így a kép jobb oldalon kilóghat. Ez nem frontend hiba, postman-nel ellenőriztem, így jönnek a képek backend-ről.
- Alapvetően nem használnék clear text kommunikációt, itt a feladat kiírásban így szerepelt, ezért kivételesen ezzel a megoldással mentem tovább.
- Fragment navigáció mellé nem használnék így másodlagos fragment-et az ajánlások miatt, de megvalósítható, így megírtam.
