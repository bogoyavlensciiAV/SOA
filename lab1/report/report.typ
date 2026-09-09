#set document(title: "Лабораторная работа №4. Apache Cassandra: кластеризация, согласованность, LSM-дерево и вторичные индексы")
#set page(
  paper: "a4",
  margin: (top: 2cm, bottom: 2cm, left: 3cm, right: 1.5cm),
  numbering: "1",
)
#set text(
  font: "New Computer Modern",
  size: 12pt,
  lang: "ru",
)
#set heading(numbering: "1.")
#set par(justify: true, leading: 0.65em)


#page(numbering: none)[
  #align(center)[
    #text(size: 12pt)[
      Федеральное государственное автономное образовательное учреждение \
      высшего образования «Национальный исследовательский университет ИТМО»
    ]

    #v(0.3cm)
    #text(size: 12pt)[Факультет Программной Инженерии и Компьютерной Техники]

    #v(3cm)

    #text(size: 16pt, weight: "bold")[Лабораторная работа №1 \ СОА \ Вариант 67205]

    #v(3cm)

    #align()[
      #grid(
        columns: (auto, 1fr),
        gutter: 0.4cm,
        [*Выполнил:*], [Вайнштейн Владислав Витальевич P3418
        \ Богоявленский Александр Викторович P3417],
        [], [],
        [*Практик:*],  [Цопа Евгений Алексеевич],
      )
    ]

    #v(1fr)
    #text(size: 12pt)[Санкт-Петербург, 2026]
  ]
]

#outline(title: "Содержание", indent: 1.5em)
#pagebreak()

= Цель работы

#image("assets/image.png")
#image("assets/image-1.png")
#image("assets/image-2.png")
#image("assets/image-3.png")

= Ход работы

#image("assets/image-4.png")
#image("assets/image-5.png")
#image("assets/image-6.png")

= Ссылка на гит

#link("https://github.com/bogoyavlensciiAV/SOA/")

= Вывод

В ходе лабораторной работы разработана OpenAPI-спецификация для двух взаимодействующих RESTful-сервисов. Все ограничения целостности данных и соответствующие HTTP-статусы учтены, а спецификация преобразована в интерактивную документацию Swagger UI, развёрнутую на сервере helios.