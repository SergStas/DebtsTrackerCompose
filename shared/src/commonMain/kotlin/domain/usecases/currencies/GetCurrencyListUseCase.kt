package domain.usecases.currencies

import domain.models.Currency

class GetCurrencyListUseCase {
    operator fun invoke() = Currency.values().toList()
}