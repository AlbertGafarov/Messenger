package ru.gafarov.Messenger.model;

public enum BetStatus {
    OFFERED // Предложен
    ,CANCEL  // Спор отклонен
    ,ACCEPTED // Принят
    ,WIN    // Победил
    ,LOSE   // Проиграл
    ,STANDOFF   // Ничья
    ,WAGERPAID  // Выигрыш оплатил
    ,WAGERRECIEVED  // Выигрыш получил
}
