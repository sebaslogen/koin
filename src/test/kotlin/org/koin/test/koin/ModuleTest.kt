package org.koin.test.koin

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.koin.Koin
import org.koin.test.koin.example.*

/**
 * Created by arnaud on 31/05/2017.
 */
class ModuleTest {

    @Test
    fun `load module`() {
        val ctx = Koin().build(SampleModuleB::class)

        ctx.assertSizes(1, 0)

        assertNotNull(ctx.get<ServiceB>())

        ctx.assertSizes(1, 1)
    }

    @Test
    fun `load module - missing dep`() {
        val ctx = Koin().build(SampleModuleB::class)

        ctx.assertSizes(1, 0)

        assertNotNull(ctx.get<ServiceB>())

        ctx.assertSizes(1, 1)
        assertNull(ctx.getOrNull<ServiceA>())
        ctx.assertSizes(1, 1)
    }

    @Test
    fun `load mulitple modules`() {
        val ctx = Koin().build(SampleModuleA::class, SampleModuleB::class)
        assertNotNull(ctx.get<ServiceB>())
        assertNotNull(ctx.get<ServiceA>())
        ctx.assertSizes(2, 2)
    }

    @Test
    fun `load mulitple modules - lazy deps`() {
        val ctx = Koin().build(SampleModuleB::class, SampleModuleA_C::class)

        assertNotNull(ctx.get<ServiceB>())
        assertNotNull(ctx.get<ServiceA>())
        assertNotNull(ctx.get<ServiceC>())
        ctx.assertSizes(3, 3)
    }

    @Test
    fun `import with lazy linking`() {
        //onLoad only ServiceB
        val ctx = Koin().build(SampleModuleA_C::class)

        ctx.assertSizes(2, 0)

        ctx.provide { ServiceB() }

        ctx.assertSizes(3, 0)

        assertNotNull(ctx.get<ServiceA>())
        assertNotNull(ctx.get<ServiceB>())
        assertNotNull(ctx.get<ServiceC>())
        ctx.assertSizes(3, 3)
    }

    @Test
    fun `missing bean component - lazy linking`() {
        val ctx = Koin().build(SampleModuleA_C::class)

        assertNull(ctx.getOrNull<ServiceA>())
        assertNull(ctx.getOrNull<ServiceC>())
        ctx.assertSizes(2, 0)
    }
}