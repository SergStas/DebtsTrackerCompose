package di

import org.kodein.di.DI
import org.kodein.di.DIAware

interface AppDiAware: DIAware {
    override val di: DI
        get() = AppDi.di
}