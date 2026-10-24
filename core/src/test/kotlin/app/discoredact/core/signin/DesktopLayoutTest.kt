package app.discoredact.core.signin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DesktopLayoutTest {
    @Test
    fun aPhoneScreenGetsTheDesktopWidthScaledToFit() {
        // A 1080 x 2000 px container at density 2.625 (a Pixel): 1280 CSS px is 3360 px, scaled by 1080/3360.
        val f = DesktopLayout.fit(1080, 2000, 2.625f)!!
        assertEquals(3360, f.widthPx)
        assertEquals(1080f / 3360f, f.scale, 0f)
        assertEquals(Math.round(2000 / (1080f / 3360f)), f.heightPx)
        // Scaled back, the page fills the container exactly.
        assertEquals(1080, Math.round(f.widthPx * f.scale))
        assertEquals(2000, Math.round(f.heightPx * f.scale))
    }

    @Test
    fun aContainerWiderThanTheDesktopIsNotEnlarged() {
        val f = DesktopLayout.fit(4000, 1000, 2f)!!
        assertEquals(4000, f.widthPx)
        assertEquals(1f, f.scale, 0f)
        assertEquals(1000, f.heightPx)
    }

    @Test
    fun anEmptyContainerOrABadDensityLaysOutNothing() {
        assertNull(DesktopLayout.fit(0, 2000, 2f))
        assertNull(DesktopLayout.fit(1080, 0, 2f))
        assertNull(DesktopLayout.fit(1080, 2000, 0f))
        assertNull(DesktopLayout.fit(1080, 2000, Float.NaN))
    }
}
